#!/bin/bash

scp build/libs/language-parser-*.jar foundry@foundry.owlbeardm.com:/home/foundry/lp/language-parser-new.jar
ssh foundry@foundry.owlbeardm.com "rm -f /home/foundry/lp/language-parser.jar"
ssh foundry@foundry.owlbeardm.com "mv /home/foundry/lp/language-parser-new.jar /home/foundry/lp/language-parser.jar"
ssh foundry@foundry.owlbeardm.com "/home/foundry/.nvm/versions/node/v14.19.2/bin/pm2 restart lp"