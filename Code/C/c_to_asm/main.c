#include <stdio.h>
#include "udp3.h"

#define PORT 25565

int main(void)
{
    char str[100];
    bool ok;

    ok = openUdpListenerPort(PORT);
    if (ok)
    {
        printf("Listening on port %d\n", PORT);
        while (true)
        {
            ok = receiveUdpData(str, sizeof(str), 1000000);
            if (ok)
            {
                printf("Received: %s\n", str);
            }
            else
            {
                printf("Receive timeout\n");
            }
        }
        closeUdpListenerPort();
    }
    else
    {
        printf("Unable to open port %d\n", PORT);
    }
    return 0;
}