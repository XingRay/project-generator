## 项目生成器

### 目标
1. 通过代码生成的方式生成项目，包括项目的结构，源码，配置，数据库脚本，环境安装脚本，部署脚本，测试脚本等。
2. 通过定义脚本或者少量的核心类快速实现整个项目。

### 短期目标
1. 通过定义一个实体类，如通过一个实体类 User 生成 一个包含User模块的项目，User模块，UserController UserService UserMapper UserDto UserVo，创建User表的sql语句。
2. 可以根据定义文件生成项目
3. 可以根据配置修改现有的项目，主要是通过代码生成的方式快速增加功能
4. 根据现有模块分析接口，自动生成各个平台的接口实现模块，自动编译、测试、代码入库、生成依赖包、生成标准文档。客户端接口实现，客户端项目实现。

### 长期目标
1. 客户端与服务端自动联调，自动测试及报告生成
2. 基于模块的技术选型，同类型的技术实现之间自动对比。