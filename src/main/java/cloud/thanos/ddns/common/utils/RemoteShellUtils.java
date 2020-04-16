package cloud.thanos.ddns.common.utils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author leanderli
 * @see
 * @since 2020.04.16
 */
public class RemoteShellUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteShellUtils.class);

    /**
     * 执行远程服务器上的shell脚本
     *
     * @param ip       服务器IP地址
     * @param port     端口号
     * @param name     登录用户名
     * @param pwd      密码
     * @param commands shell命令
     */
    public static void execShell(String ip, int port, String name, String pwd, String commands) {
        Connection conn = null;
        try {
            conn = new Connection(ip, port);
            conn.connect();
            if (conn.authenticateWithPassword(name, pwd)) {
                // Open a new {@link Session} on this connection
                Session session = conn.openSession();
                // Execute a command on the remote machine.
                session.execCommand(commands);
                try (
                        BufferedReader br = new BufferedReader(new InputStreamReader(session.getStdout()));
                        BufferedReader brErr = new BufferedReader(new InputStreamReader(session.getStderr()));
                ) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        LOGGER.info("br={}", line);
                    }
                    while ((line = brErr.readLine()) != null) {
                        LOGGER.info("brErr={}", line);
                    }
                }
                session.waitForCondition(ChannelCondition.EXIT_STATUS, 0);
                int ret = session.getExitStatus();
                LOGGER.info("getExitStatus:" + ret);
            } else {
                LOGGER.info("登录远程机器失败" + ip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
