# generate a 2048-bit RSA private key
openssl genrsa -out private_key.pem 2048

# convert private Key to PKCS#8 format (so Java can read it)
openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt

# output public key portion in DER format (so Java can read it)
openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der