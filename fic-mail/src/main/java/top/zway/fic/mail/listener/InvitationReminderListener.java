package top.zway.fic.mail.listener;

import cn.hutool.core.io.IoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import top.zway.fic.base.constant.RabbitMqConstants;
import top.zway.fic.base.entity.bo.InvitationReminderBO;
import top.zway.fic.base.entity.bo.MailMessageBO;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.MessageFormat;

/**
 * @author ZZJ
 */
@Component
@RabbitListener(queues = RabbitMqConstants.INVITATION_REMINDER_QUEUE_NAME)
@RequiredArgsConstructor
@Slf4j
public class InvitationReminderListener {
    @Value("${spring.mail.nickname}")
    private String nickname;

    private final RabbitTemplate rabbitTemplate;

    private static String INVITATION_REMINDER_TEMPLATE;

    @PostConstruct
    public static void init() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:email_template/invitation_reminder.html");
        INVITATION_REMINDER_TEMPLATE = IoUtil.read(new FileReader(file));
    }

    @RabbitHandler
    public void process(InvitationReminderBO invitationReminderBO) {
        String content = MessageFormat.format(INVITATION_REMINDER_TEMPLATE, invitationReminderBO.getInviter(),
                invitationReminderBO.getKanbanName());
        MailMessageBO mailMessage = new MailMessageBO(invitationReminderBO.getInviteeEmail(), nickname + "-协作邀请", content);
        rabbitTemplate.convertAndSend(RabbitMqConstants.MAIL_SEND_EXCHANGE_NAME, "", mailMessage);
    }
}
