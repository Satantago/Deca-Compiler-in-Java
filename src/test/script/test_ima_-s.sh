#!/bin/sh

DEBUT=$(date +%s.%N)

for i in src/test/deca/codegen/valid/provided/*.ass
do
echo "$i"



ima -s "$i" > "${i%.ass}".res

if [ $? -ne 1 ]
then
    echo  "\033[0;31mTEST IMA ERREUR\033[0m"
else
    echo  "\033[0;32mTEST IMA PASSÉ \033[0m"
fi


done

FIN=$(date +%s.%N)

echo "TEMPS D'EXECUTION TOTAL DES TESTS  : $(echo "$FIN - $DEBUT" | bc) secondes"

