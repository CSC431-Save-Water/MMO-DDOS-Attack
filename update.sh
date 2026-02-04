#!/bin/bash
# Harmless update script for Social Engineering Assignment

# 1. Define variables
TEAM_NAME="Save-Water"
FILE_PATH="$HOME/Desktop/${TEAM_NAME}.txt"
TIMESTAMP=$(date)
MEMBERS="Eli Manning, Jacob Pugh, Brandon Magana"

# 2. Terminal Output Simulation (The "Hook")
echo "Starting System Update..."
sleep 1
echo -n "[##########----------] 50% - Downloading dependencies..."
sleep 1
echo -e "\r[####################] 100% - Patch applied successfully."
echo ""

# 3. Create the file on the Desktop (The "Payload")
# Using mkdir -p ensures the Desktop directory exists if they are on a fresh VM
mkdir -p "$HOME/Desktop"
echo "Team Members: $MEMBERS" > "$FILE_PATH"
echo "Timestamp: $TIMESTAMP" >> "$FILE_PATH"

# 4. Success Confirmation
if [ -f "$FILE_PATH" ]; then
    echo "-----------------------------------------------"
    echo "SUCCESS: Security Update Installed."
    echo "Log created at: $FILE_PATH"
    echo "-----------------------------------------------"
    
    # Optional: Send a "ping" back to your server log so you know it worked
    # This will show up as a 404 in your Python terminal, which is your "signal"
    curl -s "http://10.0.101.64/success_flag_for_${TEAM_NAME}" > /dev/null
else
    echo "Error: Update failed. Please contact the administrator."
fi