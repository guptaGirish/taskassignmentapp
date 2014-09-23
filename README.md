taskassignmentapp
=================

Task Assignment App

Use case -
This app is to assign the task to person whoever is using app. Creater create task and assign it to a assignee. Assignee gets a notification when he is assigned a task. Assignee's can also send response back to creater over assigned task. Createe gets a notification on his device when he is responded back by assignee. 

This app is under development. This app has been built to demonstrate the integration of android apps with open source backend as a service named "Openmobster"(https://code.google.com/p/openmobster) so that enterprise app can be built up.

This integrated system has 2 parts -

- Cloud side  (code contained under "TaskAssignmentCloudServer" folder)
- Mobile side (code contained under "TaskAssignmentAndroidApp" folder for Android App)


"This app currently in under active development."

To start using app-
First, you will have to set up cloud server part on JBOSS server. Deployment of cloud server code is similar to as mentioned on link below -
http://openmobster.googlecode.com/svn/wiki/content/app-developer-guide/html/crud.html#d0e425
While setting up server code, you will have to make sure that you have configured one sql database with cloud server. You can configure the details mysql db in this file -
https://github.com/guptaGirish/taskassignmentapp/blob/master/TaskAssignmentCloudServer/target/classes/taskcrud.cfg.xml


Secondly, you will have to install your app on your 2 android devices. After installation of android app, you will have to register it to server, so from home screen by clicking "Register app" button and filling foem, you get activated as well registered device details with cloud server. After that you can return back to your home screen and go with "create task" button, this will list all the devices registered with server, select one of them to assign task. 



Features not completed-
- Collecting Response back from assignee is not complete for now.




