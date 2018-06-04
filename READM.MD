#####   Jenkins 使用文件集成方式
1. 配置变量 `maven`地址、`gitLab`或者`svn`地址，项目名、发布到的服务器地址，`tag`，使用哪个`profile`等
2. 拉取最新代码；
3. 项目打包`mvn clean package`
4. `sonar qube` 代码审查
5. 旧代码备份`backup`
6. `ssh` 连接到目标机器，删除旧代码，启动`service`

主要配置shell脚本，运行脚本命令实现自动化部署
项目是自己练手demo，部分jar包会下载不下来，不影响配置使用