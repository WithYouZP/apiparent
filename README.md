# apiparent
前后端分离之后的脚手架
说明：该项目是一个springboot的maven 聚合项目，分为两个模块api-common,api-core.
api-common中主要封装了一下通用的工具类，和一些对数据库方言的解析，和一下请求返回实体的封装，和一些对接口的定义。
api-core 中依赖了 api-common模块,主要是核心代码的编写，包括基础Controller和对api-common 中定义得接口的实现。
一.使用步骤
将该项目clone到自己的本地，然后将该项目install 到自己的本地仓库中。
二.新建一个springboot 项目，在该项目的pom.xml文件中 依赖该工程。
