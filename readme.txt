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