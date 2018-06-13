package utility;

import utility.readProperity.SingleTonReadMailConfigProperity;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Transport;

public class MailSender {
    private static StringBuilder mail_smtp_host = SingleTonReadMailConfigProperity.getProValue("mail_smtp_host");
    private static StringBuilder mail_smtp_socket = SingleTonReadMailConfigProperity.getProValue("mail_smtp_socket");
    private static StringBuilder mail_socket = SingleTonReadMailConfigProperity.getProValue("mail_socket");
    private static StringBuilder mail_auth = SingleTonReadMailConfigProperity.getProValue("mail_auth");
    private static StringBuilder mail_port = SingleTonReadMailConfigProperity.getProValue("mail_port");

    private static StringBuilder senderAddress = SingleTonReadMailConfigProperity.getProValue("senderAddress");
    private static StringBuilder senderKey = SingleTonReadMailConfigProperity.getProValue("senderKey");
    private static StringBuilder receiverList = SingleTonReadMailConfigProperity.getProValue("receiverList");
    private static StringBuilder mailTitle = SingleTonReadMailConfigProperity.getProValue("mailTitle");
    private static StringBuilder mailText = SingleTonReadMailConfigProperity.getProValue("mailText");
    private static StringBuilder mailAttSourceFile = SingleTonReadMailConfigProperity.getProValue("mailAttSourceFile");
    private static ArrayList<InternetAddress> list4receiver;

    public MailSender() {

    }


    public static boolean sendMail() throws Exception {
        boolean result = false;
        try {
            // 创建一个Property文件对象
            Properties props = new Properties();
            // 设置邮件服务器的信息，这里设置smtp主机名称
            props.put("mail.smtp.host", mail_smtp_host.toString());
            // 设置socket factory 的端口
            props.put("mail.smtp.socketFactory.port", mail_smtp_socket.toString());
            // 设置socket factory
            props.put("mail.smtp.socketFactory.class", mail_socket.toString());
            // 设置需要身份验证
            props.put("mail.smtp.auth", mail_auth.toString());
            // 设置SMTP的端口，QQ的smtp端口是25
            props.put("mail.smtp.port", mail_port.toString());
            // 身份验证实现
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {
                    // 第二个参数，就是我QQ开启smtp的授权码
                    return new PasswordAuthentication(senderAddress.toString(), senderKey.toString());
                }

            });


            // 创建一个MimeMessage类的实例对象
            Message message = new MimeMessage(session);
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress(senderAddress.toString()));

            list4receiver = new ArrayList<InternetAddress>();
            int length = receiverList.toString().split(";;").length;
            for (int i = 0; i < length; i++) {
                list4receiver.add(new InternetAddress(receiverList.toString().split(";;")[i]));
            }
      //      Address[] lastList = new Address[list4receiver.size()];
            // 设置收件人邮箱地址
            //   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiverList.toString()));
            for (int ind = 0; ind < list4receiver.size(); ind++) {
              //  lastList[ind] = (Address) list4receiver.get(ind);
                message.addRecipient(Message.RecipientType.TO, (Address) list4receiver.get(ind));
            }


            // 设置邮件主题
            message.setSubject(mailTitle.toString());
            // 创建一个MimeBodyPart的对象，以便添加内容
            BodyPart messageBodyPart1 = new MimeBodyPart();
            // 设置邮件正文内容
            messageBodyPart1.setText(mailText.toString());
            // 创建另外一个MimeBodyPart对象，以便添加其他内容
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            // 设置邮件中附件文件的路径
            String filename = mailAttSourceFile.toString();
            // 创建一个datasource对象，并传递文件
            DataSource source = new FileDataSource(filename);
            // 设置handler
            messageBodyPart2.setDataHandler(new DataHandler(source));
            // 加载文件
            messageBodyPart2.setFileName(filename);
            // 创建一个MimeMultipart类的实例对象
            Multipart multipart = new MimeMultipart();
            // 添加正文1内容
            multipart.addBodyPart(messageBodyPart1);
            // 添加正文2内容
            multipart.addBodyPart(messageBodyPart2);
            // 设置内容
            message.setContent(multipart);
            // 最终发送邮件
            Transport.send(message);
            System.out.println("=====邮件已经发送=====");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }


    }

    public static void main(String[] args) {
        try {
            MailSender.sendMail();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
