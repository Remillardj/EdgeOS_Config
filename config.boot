firewall {
    all-ping enable
    broadcast-ping disable
    ipv6-receive-redirects disable
    ipv6-src-route disable
    ip-src-route disable
    log-martians enable
    name LAN {
        default-action accept
        description "Allow LAN Traffic"
        enable-default-log
        rule 1 {
            action accept
            description "Allow local traffic on all protocols"
            log enable
            protocol all
        }
    }
    name WAN_IN {
        default-action drop
        description "WAN to internal"
        rule 10 {
            action accept
            description "Allow established/related"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
    }
    name WAN_LOCAL {
        default-action drop
        description "WAN to router"
        rule 10 {
            action accept
            description "Allow established/related"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
    }
    receive-redirects disable
    send-redirects enable
    source-validation disable
    syn-cookies enable
}
interfaces {
    bridge br0 {
        address 192.168.50.1/24
        aging 300
        bridged-conntrack disable
        description "Local Bridge"
        hello-time 2
        max-age 20
        priority 32768
        promiscuous enable
        stp false
    }
    ethernet eth0 {
        address dhcp
        description Internet
        duplex auto
        firewall {
            in {
                name WAN_IN
            }
            local {
                name WAN_LOCAL
            }
        }
        speed auto
    }
    ethernet eth1 {
        bridge-group {
            bridge br0
        }
        description "Local Bridge"
        duplex auto
        speed auto
    }
    ethernet eth2 {
        bridge-group {
            bridge br0
        }
        description "Local Bridge"
        duplex auto
        speed auto
    }
    ethernet eth3 {
        disable
        duplex auto
        speed auto
    }
    loopback lo {
    }
}
port-forward {
    auto-firewall enable
    hairpin-nat enable
    lan-interface br0
    wan-interface eth0
}
service {
    dhcp-server {
        disabled false
        hostfile-update disable
        shared-network-name LAN_BR {
            authoritative enable
            subnet 192.168.50.0/24 {
                default-router 192.168.50.1
                dns-server 192.168.50.1
                dns-server 8.8.8.8
                domain-name mydomain.net
                lease 86400
                start 192.168.50.2 {
                    stop 192.168.50.254
                }
            }
        }
        use-dnsmasq enable
    }
    dns {
        forwarding {
            cache-size 150
            listen-on br0
            options expand-hosts
        }
    }
    gui {
        http-port 80
        https-port 443
        older-ciphers enable
    }
    nat {
        rule 5010 {
            description "masquerade for WAN"
            outbound-interface eth0
            type masquerade
        }
    }
    ssh {
        port 22
        protocol-version v2
    }
    unms {
        disable
    }
}
system {
    domain-name myraidify.net
    host-name ubnt
    login {
        user ubnt {
            authentication {
                encrypted-password $6$71WuPugjzaP9J$BJ4p/Veo8LdnG/RzPpsq56wAiHyxg.g0wJmGq.mDZUjW9uuQGMQnV9ztlz/bl1ZVz3DjvIwCDZQlqFYC7U6zH0
            }
            level admin
        }
    }
    name-server 192.168.50.1
    name-server 208.67.222.222
    name-server 208.67.220.220
    name-server 8.8.8.8
    name-server 8.8.4.4
    ntp {
        server 0.ubnt.pool.ntp.org {
        }
        server 1.ubnt.pool.ntp.org {
        }
        server 2.ubnt.pool.ntp.org {
        }
        server 3.ubnt.pool.ntp.org {
        }
    }
    syslog {
        global {
            facility all {
                level notice
            }
            facility protocols {
                level debug
            }
        }
    }
    time-zone UTC
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "config-management@1:conntrack@1:cron@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@5:nat@3:qos@1:quagga@2:system@4:ubnt-pptp@1:ubnt-unms@1:ubnt-util@1:vrrp@1:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: v1.9.8.5012183.170825.0258 */
