基本都是java和部分算法的学习，请随意

尾部带数字

Set WshShell= WScript.CreateObject("WScript.Shell")
WshShell.AppActivate "李浩"
for i=1 to 30
WScript.Sleep 100
WshShell.SendKeys "^v"
WshShell.SendKeys i
WshShell.SendKeys "%s"
Next


尾部不带数字

set wshshell=wscript.createobject("wscript.shell") 
wshshell.AppActivate"李浩" 
for i=1 to 100    
wscript.sleep 100    
wshshell.sendKeys "^v" 
wshshell.sendKeys "%s" 
next

qq发消息（微信通用）
这个真的迷，不能过审百度都有的玩意，真的笑死

如上的代码用文本文件保存后将后缀改成vbs

可修改：for后面的是发送次数 sleep是间隔时间 AppActivate后面是你qq联系人的名字也就是你的备注

使用：
1首先打开聊天框
2输入你想发的内容
3全选复制
4双击写好的文件
5（要快要在双击之后马上点击）鼠标点击聊天框则会发送
因为你双击后就开始运行了，如果不马上点击前面的循环就没了是从你点击后才开始发送就不是从0开的的