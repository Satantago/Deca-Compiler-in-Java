#!/bin/sh

DEBUT=$(date +%s.%N)

for i in src/test/deca/syntax/invalid/provided/*.deca
do
echo "$i"



test_synt "$i" > "${i%.deca}".lis


if [ $? -ne 0 ]
then
    echo  "\033[0;32mTEST SYNTAX ERREUR ATTENDU \033[0m"
else
    echo  "\033[0;31mTEST SYNTAX PASSÉ INNATENDU \033[0m"
fi


done

FIN=$(date +%s.%N)

echo "TEMPS D'EXECUTION TOTAL DES TESTS  : $(echo "$FIN - $DEBUT" | bc) secondes"

