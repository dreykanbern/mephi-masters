#!/usr/bin/env bash

# Черновик: записываем один снимок ресурсов.
{
    date '+--- %Y-%m-%d %H:%M:%S ---'
    free -h
    df -h
    uptime
    echo
} >> monitor.log
