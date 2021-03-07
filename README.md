# Jobever

## 参考项目列表

- 豆宝社区：https://github.com/songboriceman/doubao_community_backend
- 豆宝社区的视频：https://www.bilibili.com/video/BV1Wz4y1U7vC
- 网页模板主页：https://onepagelove.com/templates/free-templates
- 部署地址：http://jobever.cn





## 页面介绍

- 主页

![image-20210307162945904](https://i.loli.net/2021/03/07/kJtlGgeXQ8uYvZm.png)

- 文章详情

![image-20210307162652195](https://i.loli.net/2021/03/07/wPFORJyVlobpCUH.png)

- 个人中心

![image-20210307163012577](https://i.loli.net/2021/03/07/hRplySda6MWZeYE.png)

- 图片上传

![image-20210307163047376](https://i.loli.net/2021/03/07/5oV7weKsrjkC6yT.png)

## 功能改进

- **添加左侧分类栏**

```
对文章进行基础的分类，求助帖发布的时候就要选择分类，根据这个分类进行检索，调用search的接口
```

- 新增一个**关于**静态页面

```
这个页面对整个网站进行介绍，包括网站用途，社区规则，开发者信息（直接用markdown写一个静态页面）
```

- **编辑问题：**

```
1.图片上传功能实现：
原项目貌似没有图片上传功能，只支持图片链接。所以最好添加图片上传功能，在粘贴的瞬间，将图片上传，并且返回为markdown文档的链接格式。

2.规范问题发帖格式，增加以下几个必填项
- 问题分类，一个下拉列表
- 问题悬赏金额

3.添加问题审核功能
- 添加的帖子不能立马公布，还需要管理员审核
```

- **将评论区直接改名为讨论区**

```
- 所有的信息都会在讨论区讨论
- 添加收藏话题的信息提醒功能，一旦该话题下有信息回复，则提醒所有关注该话题的用户
```

- 收藏功能

```
对某个帖子可以收藏该话题，方便再次浏览
```

- 结束话题

```
当某个问题解决之后，由提问者结束话题，并且弹出是否支持开源社区的二维码页面
```

- 消息提醒

```
在右上角的用户那里新增一个消息界面
- 当话题回复的时候添加消息提醒
- 当发布的话题审核通过之后提供消息提醒
- 其他消息也会在这里显示
```

## 项目结构

```zsh
├── common
│   ├── api					# 服务端返回数据结构的定义
│   ├── exception			# 服务端返回数据结构的定义
│   └── mybatisplus
├── config
├── controller
├── jwt						# JSON Web Tokens 
├── mapper
├── model
│   ├── dto					# 前端返回的数据保存为的对象
│   ├── entity
│   └── vo					# 返回给前端的数据对象类型
├── service
│   └── impl				# 
└── utils
```

## 数据库设计

- POST 专栏分类

```java
将帖子分专栏，修改BMsPOST下的：
/**
* 专栏ID，默认不分栏
*/
@TableField("section_id")
@Builder.Default
private Integer sectionId = 0;
```

