# 高校宿舍管理系统

基于 **Spring Boot + Vue** 的前后端分离宿舍管理系统，覆盖宿舍楼宇、房间床位、住宿分配、调宿申请、卫生检查、水电费账单、报修工单、访客登记等业务场景，并带一套完整的后台权限体系（用户 / 角色 / 菜单 / 字典 / 通知）。

> 前端与后端脚手架基于开源项目 [RuoYi-Vue](https://github.com/yangzongzhuan/RuoYi-Vue)（MIT 协议）搭建，在其基础上完成了宿舍业务模块与数据模型的开发。

---

## 技术栈

| 层次 | 技术 |
| --- | --- |
| 后端 | Spring Boot 2.7.0 · Java 11 · Spring Security + JWT · MyBatis-Plus 3.5.4 · PageHelper · Druid · Redis · MySQL 8 |
| 前端 | Vue 2.6 · Element UI 2.15 · Vuex · Vue Router · Axios · ECharts |
| 工具链 | EasyExcel（Excel 导入导出）· Hutool · FastJSON · Swagger（springfox 2.9）· Lombok |

服务端默认端口 **9090**，前端开发服务器默认 **80**。

---

## 功能模块

| 模块 | 说明 |
| --- | --- |
| 宿舍楼宇 `dorm_build` | 楼栋的增删改查、男/女宿舍区分 |
| 宿舍房间 `dorm_room` | 房间与床位维护、容量与当前入住人数 |
| 住宿分配 `dorm_room_user` | 学生与床位的绑定关系 |
| 调宿管理 `adjust_room` | 调宿申请与审批流转 |
| 卫生检查 `dorm_check` | 检查记录与评分 |
| 水电费 `order` | 费用账单生成与状态跟踪 |
| 报修工单 `repair` | 学生报修与处理进度 |
| 访客登记 `visitor` | 来访人员登记与离校时间 |
| 数据看板 | ECharts 图表统计（入住率、报修趋势等） |
| 系统管理 | 用户 / 角色 / 菜单 / 部门 / 字典 / 参数 / 通知公告 / 操作日志（来自 RuoYi 脚手架） |

---

## 目录结构

```
.
├── dorm/                     前端（Vue 2 + Element UI）
│   ├── src/api/              按模块划分的接口封装
│   ├── src/views/            页面：dorm/adjust/check/order/repair/visitor/system…
│   ├── src/utils/request.js  axios 拦截器与统一错误处理
│   └── vue.config.js         开发代理与打包配置
└── dormManager/              后端（Spring Boot）
    ├── src/main/java/com/it/dorm/
    │   ├── controller/       接口层
    │   ├── service/          业务层
    │   ├── mapper/           MyBatis-Plus Mapper
    │   ├── domain/           实体与 DTO/VO
    │   ├── config/           安全、Redis、Swagger、跨域配置
    │   └── common/           统一返回体、分页、常量
    ├── src/main/resources/
    │   ├── application.yml       主配置
    │   ├── application-dev.yml   开发环境配置
    │   └── mapper/               MyBatis XML
    └── sql/dorm.sql              建表语句与初始数据
```

---

## 快速开始

### 1. 准备环境

- JDK 11、Maven 3.6+
- MySQL 8.0
- Redis 5.0+
- Node.js 14+（前端）

### 2. 建库并导入数据

```sql
CREATE DATABASE `my_dorm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

然后导入 `dormManager/sql/dorm.sql`。

### 3. 修改配置

编辑 `dormManager/src/main/resources/application-dev.yml`，把占位符换成你自己的值：

```yaml
spring:
  datasource:
    username: root
    password: 你的数据库密码          # 仓库里是占位符 your-mysql-password
  redis:
    host: localhost
    port: 6379
    # password: 你的 Redis 密码      # 有密码时取消注释

token:
  secret: 换成一串足够长的随机字符串   # 仓库里是占位符，务必改掉
```

### 4. 启动后端

```bash
cd dormManager
mvn spring-boot:run
```

接口文档：<http://localhost:9090/swagger-ui.html>

### 5. 启动前端

```bash
cd dorm
npm install --registry=https://registry.npmmirror.com
npm run dev
```

浏览器打开 <http://localhost>。

### 默认账号

| 账号 | 密码 | 说明 |
| --- | --- | --- |
| `admin` | `admin123` | 超级管理员（RuoYi 脚手架内置账号） |

---

## 安全说明（重要）

本仓库在公开前做了清理，请注意以下几点：

1. **不含任何真实凭据。** 数据库口令与 JWT 签名密钥在 `application-dev.yml`、`application.yml` 中均已替换为占位符，使用前必须自行填写。
2. **开发期产生的敏感数据未入库。** 已在 `.gitignore` 中排除：
   - `Redis` 转储文件（`*.rdb`）——其中包含登录授权缓存和用户记录（用户名、密码哈希、盐、手机号）；
   - `dormManager/files/`——后台上传的文件目录；
   - `node_modules/`、`target/`、`out/`、`.idea/` 等依赖与编译产物。
3. **前端的 RSA 密钥是示例密钥。** `dorm/src/utils/jsencrypt.js` 中保留了一对用于登录密码加密的 RSA 密钥。它属于演示用途，上线前请自行生成新的密钥对替换，不要直接用于生产环境。

---

## 已知问题与改进方向

这些是开发过程中留下的技术债，写在这里方便后续迭代：

- **`fastjson 1.2.33` 版本过旧**，存在已知的反序列化安全风险，建议升级到 `fastjson2` 或改用 Jackson。
- **`jjwt 0.9.1` 已停止维护**，且依赖 `javax.xml.bind`（因此需要额外引入 JAXB），建议升级到 0.11+ 并使用 `jakarta` 命名空间。
- **`springfox 2.9.2` 并不官方支持 Spring Boot 2.7**，配置里的 `matching-strategy: ant_path_matcher` 就是为了绕过路径匹配的兼容问题；可考虑迁移到 `springdoc-openapi`。
- **密码加密目前只有前端一层**：RSA 加密依赖随前端下发的密钥，防护意义有限，生产环境应配合 HTTPS 使用。
- 缺少单元测试与接口自动化测试。
- 前端仍在使用 Vue 2，可评估迁移到 Vue 3。

---

## 许可

MIT License。脚手架部分版权归 [RuoYi-Vue](https://github.com/yangzongzhuan/RuoYi-Vue) 项目所有，同样以 MIT 协议开源。
