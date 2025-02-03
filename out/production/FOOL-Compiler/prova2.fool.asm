push 0
push 5
push 3
add
push function0
lfp
push -2
add
lw
push 8
beq label6
push 0
b label7
label6:
push 1
label7:
push 1
beq label4
push 0
push 1
add 
push 2
beq label8
push 0
b label9
label8:
push 1
label9:
b label5
label4:
push 1
push 1
beq label10
push 0
push 1
beq label10
push 0
b label11
label10:
push 1
label11:
label5:
print
halt

function0:
cfp
lra
push 1
lfp
push -2
add
lw
lfp
push 1
add
lw
lfp
push 2
add
lw
beq label2
push 0
b label3
label2:
push 1
label3:
beq label0
push 0
b label1
label0:
push 1
label1:
stm
pop
sra
pop
pop
pop
sfp
ltm
lra
js