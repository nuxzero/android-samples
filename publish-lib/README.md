# Publishing public repository to Sonatype

This repo contain description about publishing public repository from **Github** to **Sonatype**. 

1. First you need to create an account for [Create Jira account](https://issues.sonatype.org/secure/Signup!default.jspa)
2. Next create a new project ticket


### Signing

```
signing.keyId=[GPG-KEY-ID]
signing.password=[GPG-PASSWORD]
signing.secretKeyRingFile=[GPG-FILE-PATH]

ossrhUsername=[OSS.SONATYPE-USERNAME]
ossrhPassword=[OSS.SONATYPE-PASSWORD]
```
### Error

`Failed: Signature validation` cause you did not upload your public key yet. So, you can export public key and upload to [http://pool.sks-keyservers.net:11371](http://pool.sks-keyservers.net:11371)

 **References**
 [OSSRH guide](https://central.sonatype.org/pages/ossrh-guide.html)

