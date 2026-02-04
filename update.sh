#!/bin/bash
# Harmless update script for Social Engineering Assignment

# 1. Define variables
TEAM_NAME="Save-Water"
FILE_PATH="$HOME/Desktop/${TEAM_NAME}.txt"
TIMESTAMP=$(date)
MEMBERS="Eli Manning, Jacob Pugh, Brandon Magana"

# 2. Create the file on the Desktop
echo "Team Members: $MEMBERS" > "$FILE_PATH"
echo "Timestamp: $TIMESTAMP" >> "$FILE_PATH"

# 3. Friendly message for the user
echo "System update complete. Check your desktop for the log."