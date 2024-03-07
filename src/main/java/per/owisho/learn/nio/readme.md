##package info
nio learn
##reference website
https://jenkov.com/tutorials/java-nio/channels.html
##nio core component
channel,buffer,selector
##常用函数功能
flip:写模式切换为读模式，position重置为0，limit设置为之前position的位置
rewind:将position重置为0，让buffer内容可以重复读取（读取模式下使用）
clear:读模式切换为写模式，position重置为0，limit设置为capacity
compat:读模式切换为写模式，将未读取内容放置到buffer的开始位置，position设置为剩余内容的长度，limit设置为capacity
mark:标记某个位置，与reset配合使用，reset将position重置为mark标记的位置
