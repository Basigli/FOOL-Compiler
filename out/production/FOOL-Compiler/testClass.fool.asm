push 0
lhp

push first
lhp
sw
lhp
push 1
add
shp
push 5
lhp
sw
lhp
push 1
add
shp
lhp
push 9998
lw
sw
lhp
push 1
add
shp
lhp
lhp
push 1
add
shp
push 1
halt

function0:
cfp
lra
push 0
stm
sra
pop
sfp
ltm
lra
js