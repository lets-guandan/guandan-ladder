# guandan-ladder 掼蛋天梯榜

![version](https://img.shields.io/badge/version-1.0.0-blueviolet.svg)
[![license](https://img.shields.io/badge/license-MIT-ff69b4.svg)](https://mit-license.org/license.html)
[![springboot](https://img.shields.io/badge/springboot-2.3.0.RELEASE-orange.svg)](https://spring.io/projects/spring-boot)

## 项目介绍
记录每一次紧张刺激的掼蛋战绩，也是学习和练习前后端编程技术的项目；  登录访问地址：http://guandan.ballcat.cn/
## 文档地址
- [swagger]

### 功能点：
- 1、登录；
- 2、排行榜（胜场&胜率）；
- 3、历史战绩列表；
- 4、待确认战绩列表；
- 5、提交战绩；
- 6、确认战绩（4人确认方可）；
### 技术点：
- 前端vue；
- 后端springboot+mybatis；
- 数据库mysql

### 待办：
- 当时打牌照片上传
- 战绩提交消息推送全员留存记录

#

```
## 代码结构
├─guandan-ladder
  ├─guandan-ladder-backend 后端
  ├─guandan-ladder-frontend 前端
```


## git 分支开发规约

- 版本号：<主版本>.<次版本>.<增量版本>-<代号>
 例如：1.0.0-SNAPSHOT 
- 合并代码

- 本地启动步骤
  1、在guandan-ladder下clean，然后package，启动后端服务，前提要有mysql数据库，或者去node官网下载nodejs 安装一路下一步，可以不用package实现实时加载
  2、访问localhost:11111  或者直接访问前端地址http://localhost:3000
  3、安装docker desktop pull mysql镜像，启动containers设置端口和环境变量MYSQL_ROOT_PASSWORD添加密码，创建数据库 guandan_ladder 建表添加用户和数据
  4、commit前需调整格式，点击maven插件javaformat

## FAQ
- 在使用中有任何问题，欢迎反馈


## [LICENSE](LICENSE)
