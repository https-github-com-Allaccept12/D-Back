package TeamDPlus.code.alarm.firebase;

import TeamDPlus.code.domain.account.Account;

@FunctionalInterface
public interface NotificationMessageGenerator {

    String generateNotificationMessage(Account sender, Account receiver);
}
