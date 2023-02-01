package com.project.community.domain.notification.controller;

import com.project.community.common.CurrentUser;
import com.project.community.domain.notification.Notification;
import com.project.community.domain.notification.repository.NotificationRepository;
import com.project.community.domain.notification.service.NotificationService;
import com.project.community.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;

    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public String getNotifications(@CurrentUser User user, Model model){
        List<Notification> notificationList = notificationRepository.findByUserAndCheckedOrderByCreatedDateTimeDesc(user,false);
        long numberOfChecked = notificationRepository.countByUserAndChecked(user, true);
        categorizeNotifications(model, notificationList, numberOfChecked, notificationList.size());
        model.addAttribute("isNew", true);
        notificationService.markAsRead(notificationList);
        return "notification/list";
    }

    @GetMapping("/notifications/old")
    public String getOldNotifications(@CurrentUser User user, Model model){
        List<Notification> notificationList = notificationRepository.findByUserAndCheckedOrderByCreatedDateTimeDesc(user, true);
        long numberOfNotChecked = notificationRepository.countByUserAndChecked(user, false);
        categorizeNotifications(model, notificationList, notificationList.size(), numberOfNotChecked);
        model.addAttribute("isNew", false);
        return "notification/list";
    }

    @PostMapping("/notifications")
    public String deleteNotifications(@CurrentUser User user){
        notificationRepository.deleteByUserAndChecked(user, true);
        return "redirect:/notifications";
    }

    private void categorizeNotifications(Model model, List<Notification> notificationList,
                                             long numberOfChecked, long numberOfNotChecked) {
        List<Notification> newStudyNotifications = new ArrayList<>(); //새로운 스터디 알림
        List<Notification> enrollmentNotifications = new ArrayList<>();  //스터디 등록 알림
        List<Notification> updateStudyNotifications = new ArrayList<>(); //스터디 업데이트 알림
        for (var notification: notificationList){
            switch (notification.getNotificationType()){
                case CREATED: newStudyNotifications.add(notification); break;
                case ENROLLMENT: enrollmentNotifications.add(notification); break;
                case UPDATED: updateStudyNotifications.add(notification); break;
            }
        }
        model.addAttribute("numberOfNotChecked", numberOfNotChecked);  //확인 하지 않은 알람 수
        model.addAttribute("numberOfChecked", numberOfChecked);  //확인한 알람 수
        model.addAttribute("notificationList", notificationList);
        model.addAttribute("newStudyNotifications", newStudyNotifications);
        model.addAttribute("enrollmentNotifications", enrollmentNotifications);
        model.addAttribute("updateStudyNotifications", updateStudyNotifications);
    }
}
