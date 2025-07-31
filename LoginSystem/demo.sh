#!/bin/bash

echo "==================================="
echo "    Java 登录系统演示"
echo "==================================="

echo "1. 编译Java源代码..."
javac -d . -cp . src/main/java/com/login/model/User.java \
                 src/main/java/com/login/dao/UserDao.java \
                 src/main/java/com/login/dao/impl/UserDaoImpl.java \
                 src/main/java/com/login/service/LoginService.java \
                 src/main/java/com/login/ui/ConsoleLoginApp.java

if [ $? -eq 0 ]; then
    echo "✓ 编译成功！"
else
    echo "✗ 编译失败！"
    exit 1
fi

echo ""
echo "2. 项目结构："
echo "LoginSystem/"
find . -name "*.java" | head -10 | sed 's/^/  /'

echo ""
echo "3. 预置测试账户："
echo "  用户名: admin     密码: admin123"
echo "  用户名: testuser  密码: test123"

echo ""
echo "4. 运行登录系统："
echo "  使用命令: java -cp . com.login.ui.ConsoleLoginApp"

echo ""
echo "演示完成！您现在可以运行上述命令来使用登录系统。"