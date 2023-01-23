#!/bin/sh

DEBUT=$(date +%s.%N)

decac

FIN=$(date +%s.%N)

echo "TEMPS D'EXECUTION TOTAL DES TESTS  : $(echo "$FIN - $DEBUT" | bc) secondes"

