#!/bin/sh

DEBUT=$(date +%s.%N)

for i in src/test/deca/codegen/valid/provided/*.deca
do
echo "$i"



decac -p "$i" > "${i%.deca}".ass


if [ $? -ne 0 ]
then
    echo  "\033[0;31mTEST DECAC ERREUR\033[0m"
else
    echo  "\033[0;32mTEST DECAC PASSÉ \033[0m"
fi


done

FIN=$(date +%s.%N)

echo "TEMPS D'EXECUTION TOTAL DES TESTS  : $(echo "$FIN - $DEBUT" | bc) secondes"

