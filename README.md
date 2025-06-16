# 项目文档

## 项目概述
本项目包含前端管理控制台 `admin-console` 和后端服务 `BackendService` 两部分。前端使用 Vue 3 和 Vite 构建用户界面，后端基于 Spring Boot 搭建，提供 RESTful API 服务，实现了用户认证、顾客管理、菜品管理、订单管理等功能。

## 项目结构
```
demo/
├── admin-console/        # 前端管理控制台项目
├── BackendService/       # 后端服务项目
├── .gitignore            # Git 忽略文件配置
├── .idea/                # IntelliJ IDEA 项目配置文件
└── pom.xml               # Maven 项目配置文件
```

## 前端项目（admin-console）

### 推荐的开发环境设置
[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar)（并禁用 Vetur）

### 项目设置
#### 安装依赖
```sh
npm install
```

#### 开发环境编译和热重载
```sh
npm run dev
```

#### 生产环境编译和压缩
```sh
npm run build
```

### 前端主要依赖
- `axios`：用于发送 HTTP 请求。
- `vue`：核心 Vue 3 框架。
- `vue-router`：Vue 路由管理。
- `vxe-table`：表格组件库。
- `xe-utils`：工具函数库。

### 前端路由
前端路由定义在 `src/router/index.js` 中，包含以下主要路由：
- `/login`：登录页面
- `/dashboard`：仪表盘页面
- `/customers`：顾客列表页面
- `/dish`：菜品列表页面
- `/order`：订单页面
- `/employee`：员工列表页面
- `/desk`：餐台列表页面
- `/historical-orders`：历史订单页面
- `/order-details/:orderId`：订单详情页面

## 后端项目（BackendService）

### 项目依赖
后端项目使用 Maven 进行依赖管理，主要依赖如下：
- `spring-boot-starter-data-jpa`：Spring Boot JPA 支持。
- `spring-boot-starter-web`：Spring Boot Web 支持。
- `mysql-connector-j`：MySQL 数据库连接驱动。
- `mapstruct`：对象映射工具。
- `lombok`：简化 Java 代码开发。

### 数据库配置
数据库配置在 `src/main/resources/application.yaml` 中：
```yaml
spring:
    application:
        name: BackendService
    datasource:
        url: jdbc:mysql://localhost:3306/demo
        username: 用户名
        password: 密码
        driver-class-name: com.mysql.cj.jdbc.Driver
    jpa:
        show-sql: true
        hibernate:
            ddl-auto: update
```

### 后端主要接口
#### 顾客管理
- `CustomerController`：提供创建顾客接口。
```java
@GetMapping("/{id}")
public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
    try {
        CustomerDTO responseDTO = customerService.getCustomerById(id);
        return ResponseEntity.ok(responseDTO);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    } catch (Exception e) {
        return ResponseEntity.internalServerError().build();
    }
}
```

#### 菜品管理
- `DishController`：提供菜品的增删改查接口。

#### 订单管理
- `OrderController` 和 `OrderItemController`：提供订单和订单项的管理接口。

#### 员工管理
- `EmployeeController`：提供员工的增删改查接口。
```java
@RestController
@RequestMapping("api/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        try {
            List<EmployeeDTO> responseDTO = employeeService.getAllEmployees();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 其他接口方法...
}
```

#### 餐台管理
- `DeskController`：提供餐台的增删改查以及开关餐台接口。
```java
// 打开餐台
@PostMapping("/{id}/open")
public ResponseEntity<DeskDTO> openDesk(@PathVariable Long id) {
    try {
        DeskDTO responseDTO = deskService.openDesk(id);
        return ResponseEntity.ok(responseDTO);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    } catch (Exception e) {
        return ResponseEntity.internalServerError().build();
    }
}
```

#### 用户认证
- `AuthController`：提供用户登录认证接口。

## 启动项目
### 前端项目启动
在 `admin-console` 目录下执行以下命令启动开发服务器：
```sh
npm run dev
```

### 后端项目启动
在 `BackendService` 项目中，运行 `BackendServiceApplication` 类的 `main` 方法启动 Spring Boot 应用。

## 注意事项
- 请确保数据库服务正常运行，并且数据库配置信息正确。
- 前端项目默认访问地址为 `http://localhost:5173`，后端项目默认访问地址为 `http://localhost:8080`。
