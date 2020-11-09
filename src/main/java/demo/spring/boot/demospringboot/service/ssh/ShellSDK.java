package demo.spring.boot.demospringboot.service.ssh;

import com.jcraft.jsch.*;
import demo.spring.boot.demospringboot.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShellSDK {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String id;
    //远程主机的ip地址
    private String ip;
    //远程主机登录用户名
    private String username;
    //远程主机的登录密码
    private String password;
    //设置ssh连接的远程端口
    private int port = 22;
    //保存输出内容的容器
    private ArrayList<String> stdout;


    private Session session;

    private ChannelExec channelExec;

    private BufferedReader input;

    private ChannelShell channelShell;

    public ShellSDK() {
    }

    /**
     * 初始化登录信息
     *
     * @param ip
     * @param username
     * @param password
     */
    public ShellSDK(final String ip, final String username, final String password, final int port) {
        this.id = UUIDUtils.generateUUID();
        this.ip = ip;
        this.username = username;
        this.password = password;
        this.port = port;
        stdout = new ArrayList<>();
    }


    public ShellSDK login() throws JSchException {
        JSch jsch = new JSch();
        //创建session并且打开连接，因为创建session之后要主动打开连接
        this.session = jsch.getSession(this.username, this.ip, this.port);
        this.session.setPassword(this.password);
        this.session.setConfig("StrictHostKeyChecking", "no");
        this.session.connect();
        this.channelExec = (ChannelExec) this.session.openChannel("exec");
        this.channelShell = (ChannelShell) session.openChannel("shell");
        channelShell.connect(3000);
        return this;
    }

    /**
     * 执行命令
     *
     * @param command
     * @return
     * @throws JSchException
     * @throws IOException
     */
    public int execute(final String command) throws JSchException, IOException {
        //开启执行通道
//        this.channelExec = (ChannelExec) this.session.openChannel("exec");
        int returnCode = 0;
        channelExec.setCommand(command);
        channelExec.setInputStream(null);
        this.input = new BufferedReader(new InputStreamReader
                (channelExec.getInputStream()));

//        channelExec.connect();
        //接收远程服务器执行命令的结果
        String line;
        while ((line = input.readLine()) != null) {
            stdout.add(line);
        }
//        input.close();

        // 得到returnCode
        if (channelExec.isClosed()) {
            returnCode = channelExec.getExitStatus();
        }
        //关闭执行通道
//        this.channelExec.disconnect();
        return returnCode;
    }

    /**
     * execute 加强版
     * 执行命令 + 去除颜色返回
     * 解决execute获取返回的等待问题及颜色问题
     *
     * @param cmd
     * @return
     */
    public List<String> executeSup(String cmd) {
        logger.info("#命令执行# cmd:{}", cmd);
        List<String> response = new ArrayList<>();
        try {
            InputStream input = channelShell.getInputStream();
            OutputStream output = channelShell.getOutputStream();
            output.write((cmd + " \n\r").getBytes());
            output.flush();
            TimeUnit.SECONDS.sleep(1);
            byte[] tmp = new byte[1024];
            int end = input.available();
            StringBuffer buffer = new StringBuffer(1024);
            while (end > 0) {
                int i = input.read(tmp, 0, 1024);
                end = input.available();
                if (i < 0)
                    break;
                buffer.append(new String(tmp, 0, i));
            }
            LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(buffer.toString()));
            String line;
            while (null != (line = lineNumberReader.readLine())) {
                response.add(line);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;

    }

    /**
     * execute 加强版
     * 执行命令 + 去除颜色返回
     * 解决execute获取返回的等待问题及颜色问题
     * +返回的是二进制
     *
     * @param cmd
     * @return
     */
    public byte[] executeSupTermJs(String cmd) {
        logger.info("#命令执行# cmd:{}", cmd);
        List<String> response = new ArrayList<>();
        StringBuffer buffer = new StringBuffer(1024);
        try {
            InputStream input = channelShell.getInputStream();
            OutputStream output = channelShell.getOutputStream();
            output.write((cmd).getBytes());
            output.flush();
            TimeUnit.MILLISECONDS.sleep(100);
            byte[] tmp = new byte[1024];
            int end = input.available();
            while (end > 0) {
                int i = input.read(tmp, 0, 1024);
                end = input.available();
                if (i < 0)
                    break;
                buffer.append(new String(tmp, 0, i));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return buffer.toString().getBytes();

    }

    /**
     * 关闭当前连接
     */
    public void close() {
        this.session.disconnect();
    }


    /**
     * 获取shell的连接状态
     *
     * @return
     */
    public boolean isConnect() {
        return this.session.isConnected() && this.channelShell.isConnected();
    }

    /**
     * get stdout
     *
     * @return
     */
    public ArrayList<String> getStandardOutput() {
        return stdout;
    }

    public String getId() {
        return id;
    }
}