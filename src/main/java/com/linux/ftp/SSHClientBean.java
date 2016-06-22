package com.linux.ftp;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.common.Constant;
import com.ailk.common.ErrorTypeEnum;
import com.ailk.gather.exception.GatherException;
import com.ailk.gather.util.ssh.SSHXmlConfigUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 功能：ssh采集数据(可以交互)
 * @author jian
 * @since 2015-2-4
 */
public class SSHClientBean {
	private static Log log = LogFactory.getLog(SSHClientBean.class);

	private String ip;

	private String username;

	private String password;

	private int port = 22;
	
	//设备类型
	private String deviceType;
	
	private Session session = null;
	
	private Channel channel=null;
	
	private InputStream inStream=null;
	
	private OutputStream osStream=null;
	
	//用于存放命令
	private BlockingQueue<String> queueCommand=new ArrayBlockingQueue<String>(10);
	
	//每条命令执行完后的返回数据
	private StringBuffer reponseMessage=new StringBuffer();
	
	//是否已经读取完
	private boolean isFinishRead=false;
	
	
	private SSHConfig config=null;
	
	//用于处理子线程的异常
	List<Exception> exceptionList=new ArrayList<Exception>();
	
	/**
	 * 
	 * @param ip ip地址
	 * @param username ssh的用户名
	 * @param password 密码
	 * @param deviceType 设备类型
	 */
	public SSHClientBean(String ip, String username, String password,String deviceType) {
		this.ip = ip.trim();
		this.username = username.trim();
		this.password = password.trim();
		this.deviceType=deviceType.trim();
		//获取配置的设备
		config=SSHXmlConfigUtil.getInstance().getSSHConfig(this.deviceType);
		if(config==null){
			throw new GatherException(ErrorTypeEnum.TerminalTypeError,"not found the device");
		}
	}
	
	
	/**
	 * 功能：发送命令
	 * @param command 执行的命令
	 * @return 执行命令后返回的数据
	 */
	public String  sendCommand(String command){
		try {
			//清空返回来的数据
			reponseMessage.setLength(0);
			//利用阻塞队列将命令存起来给write线程去take
			queueCommand.put(command);
			//等待数据读取完
			while(!isFinishRead){
				Thread.sleep(10);
			}
			isFinishRead=false;
			if(exceptionList.size()>0){
				throw exceptionList.get(0);
			}
		  return 	reponseMessage.toString();
		}catch (Exception e) {
			log.error("excute the command '"+command+"' have an exception:"+e.getMessage());
			//如果发送异常则关闭资源
			logout();
			throw new GatherException(ErrorTypeEnum.ExecuteScriptTimeOut,e.getMessage());
		}
		
	}
	
	

	
	/**
	 * 判断是否包含分页符,如果包含,则返回匹配到的字符true,否则返回false
	 * @param moreEchoInfoArray
	 * @return
	 */
	public  boolean hasMoreInfo(String result,String[] moreEchoInfoArray) {
		for(String s : moreEchoInfoArray) {
			Pattern pattern = Pattern.compile(s);
			Matcher matcher = pattern.matcher(result);
			if(matcher.find()){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 判断命令执行完后是否已经结束
	 * @param message 返回的数据
	 * @param lastLineFlags 结束的标志
	 * @return true:结束 false:没结束
	 */
	public boolean isEndCommand(String message,String [] lastLineFlags){
		
		for(String s : lastLineFlags) {
			Pattern pattern = Pattern.compile(s);
			Matcher matcher = pattern.matcher(message);
			if(matcher.find()){
				return true;
			}
		}
		return false;
	}
	

	
	/**
	 * 退出系统
	 */
	public  void logout(){
		try {
				queueCommand.put(config.getQuitCommand());
				//关闭前ssh退出系统
				while(true){
					if(queueCommand.size()==0){
						break;
					}
					Thread.sleep(30);
				}
				if(osStream!=null){
					osStream.close();
				}
				if(inStream!=null){
					inStream.close();
				}
				if(channel!=null){
					channel.disconnect();
				}
				if(session!=null){
					session.disconnect();
				}
			} catch (Exception e) {
				log.error(String.format("close the resource have an error:%s",e.getMessage()));
				throw new GatherException(ErrorTypeEnum.OtherException,"logout have an exception");
			}
	}
	
	/**
	 * ssh交互的连接
	 */
	public  void connect() throws Exception {
        try {
        	session = (new JSch()).getSession(this.username, this.ip, this.port);
        	session.setPassword(this.password);
        	session.setUserInfo(Constant.defaultUserInfo);
        	session.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
        	session.connect(20000);
        	channel=session.openChannel("shell");
        	inStream = channel.getInputStream();  
    		osStream = channel.getOutputStream();
    		channel.connect(10000);
    		//输入命令的线程
            Write w=new Write(osStream);
            //读取命令返回数据的线程
            Read r=new Read(inStream);
            new Thread(w).start(); 
            new Thread(r).start();
            //等待读取的连接信息
			while(!isFinishRead){
				Thread.sleep(10);
			}
			isFinishRead=false;
		} catch (JSchException e) {
			log.error("Connect to "+ip+":"+port+" failed,please check your username and password!");
			throw new GatherException(ErrorTypeEnum.UsernamePasswordError,"Connect to "+ip+":"+port+" failed,please check your username and password!");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			log.error("connect to the system have an error:"+e.getMessage());
			throw new GatherException(ErrorTypeEnum.OtherException,"connect to the system have an error:"+e.getMessage());
		}
      
	}
	
	
	
	/**
	 * 获得分割后字符串数组的最后一行(非空)
	 * @param sources 源字符串
	 * @return 最后一行非空字符串
	 */
	 private  String getLastLine(String sources){
		String [] lines=sources.split("\n");
	    return lines[lines.length-1];
	}
	

	public static void main(final String[] args) throws Exception {
		SSHClientBean sshExecutor = new SSHClientBean("221.130.208.1","123", "123456","huawei");
		sshExecutor.connect();
		//sshExecutor.sendCommand("?");
		//sshExecutor.sendCommand("help");
		System.out.println(sshExecutor.sendCommand("?"));
		System.out.println(sshExecutor.sendCommand("help"));
		System.out.println(sshExecutor.sendCommand("display version"));
		sshExecutor.logout();
	}
	
	
	class Read implements Runnable{
		
		InputStream is;
		
		public Read(InputStream is){
			this.is=is;
		}

		public void run()  {
		    log.info("start to read");
		    final byte [] buffer=new byte[1024];
		    ExecutorService executor=null;
		    //读取数据的阻塞时的超时时间
		    int readTimeout=config.getEchoReadTimeout();
		    //读取数据的全部时间
		    int timeout=config.getTimeout();
		    try{
				 executor = Executors.newFixedThreadPool(1); 
				 //这个线程主要是用于处理读取数据read阻塞超时的处理
				 Callable<Integer> readTask = new Callable<Integer>() {
					public Integer call() throws Exception {
						return inStream.read(buffer);
					}
				};
				int readByte = 1;
				long beginTime=System.currentTimeMillis();
				while (readByte >= 0) {
					Future<Integer> future = executor.submit(readTask);
					//timeout
					readByte = future.get(readTimeout, TimeUnit.MILLISECONDS);
					String message="";
					if (readByte >= 0){
						 message=new String(buffer,0,readByte);
						 reponseMessage.append(message);
					}
					String lastLineText=getLastLine(reponseMessage.toString());
					//如果返回的数据是分页的，那么处理的时候要发送多一个换行符
					if(hasMoreInfo(lastLineText,config.getMoreEchoInfoArray())){
						queueCommand.put(" ");
					}
					//根据结束符判断第一次命令结束
					if(isEndCommand(lastLineText,config.getCommandEndTemplateArray())){
						isFinishRead=true;
					}
					//用户统计timeout
					long endTime=System.currentTimeMillis();
					if((endTime-beginTime)>timeout){
						isFinishRead=true;
						log.error("read the response message timeout");
						break;
					}
					
				}
				
			}catch (TimeoutException e) {
				log.error("read the response message timeout");
				exceptionList.add(new GatherException(ErrorTypeEnum.ExecuteScriptTimeOut,"read the response message timeout"));
			}
		    catch (Exception e) {
		    	//e.printStackTrace();
		    	log.error("read the response message have an exception:"+e.getMessage());
		    	exceptionList.add(new GatherException(ErrorTypeEnum.OtherException,"read the response message have an exception:"+e.getMessage()));
			}finally{
				 isFinishRead=true;
				 executor.shutdownNow();
				 log.info("finish to read the data!");
			}
		}
	}
	
	
	class Write implements Runnable  {  
        private OutputStream out;  
        
      
        Write(OutputStream out) {  
            this.out = out; 
        }  
        public void run() {  
            try {  
            	while(true){
            		//从阻塞队列中拿出一条命令执行，如果没有命令则阻塞等待
            		String command= queueCommand.take();
            		if(" ".equals(command)){
            			out.write((command).getBytes()); 
            		}else{
            			log.info("ready to execute the command:"+command);
            			out.write((command+"\n").getBytes());
            		}
            		out.flush();
            		if(command.equals(config.getQuitCommand())){
            			log.info("exit the system");
            			break;
            		}
            	}
            } catch (Exception e) {  
            	log.error("execute the command have an error:"+e.getMessage());
            }  finally {
            	IOUtils.closeQuietly(out);
            }
        }  
      
    }  
	/**
	 * 默认用户信息
	 */
	public static final UserInfo defaultUserInfo = new UserInfo() {
		public String getPassphrase() {
			return null;
		}

		public String getPassword() {
			return null;
		}

		public boolean promptPassword(String arg0) {
			return false;
		}

		public boolean promptPassphrase(String arg0) {
			return false;
		}

		public boolean promptYesNo(String arg0) {
			return true;
		}

		public void showMessage(String arg0) {
		}
	};
	
}
