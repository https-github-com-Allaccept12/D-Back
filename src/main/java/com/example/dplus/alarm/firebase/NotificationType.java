//package com.example.dplus.alarm.firebase;
//
//import com.example.dplus.domain.account.Account;
//
//public enum NotificationType {
//    POST_SELECTED(((sender, receiver) ->
//            sender.getNickname() + " 님이 " + receiver.getNickname() + " 님에게 글을 채택했습니다"));
////    POST_TAGGED(((sender, receiver) ->
////            receiver.getNickname() + " 님이 " + sender.getNickname() + " 님의 글에 태그되었습니다"));
//
//    private NotificationMessageGenerator notificationMessageGenerator;
//
//    NotificationType(NotificationMessageGenerator notificationMessageGenerator) {
//        this.notificationMessageGenerator = notificationMessageGenerator;
//    }
//
//    public String generateNotificationMessage(Account sender, Account receiver) {
//        return notificationMessageGenerator.generateNotificationMessage(sender, receiver);
//    }
//}
