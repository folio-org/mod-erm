BASEDIR=$(dirname "$0")
# echo Please make sure you have run ./gradlew clean generateDescriptors before starting this script
pushd "$BASEDIR/../service"

# Check for decriptor target directory.

DESCRIPTORDIR="build/resources/main/okapi"

if [ ! -d "$DESCRIPTORDIR" ]; then
    echo "No descriptors found. Let's try building them."
    ./gradlew generateDescriptors
fi

DEP_DESC=`cat ${DESCRIPTORDIR}/DeploymentDescriptor.json`
SVC_ID=`echo $DEP_DESC | jq -rc '.srvcId'`
INS_ID=`echo $DEP_DESC | jq -rc '.instId'`

echo Service id $SVC_ID Inst id $INS_ID

printf "\n\nremove tenant deployment\n"
curl -XDELETE "http://localhost:9130/_/proxy/tenants/diku/modules/${SVC_ID}"

printf "\n\ndelete service instance\n"
curl -XDELETE "http://localhost:9130/_/discovery/modules/${SVC_ID}/${INS_ID}"

printf "\n\ndelete module descriptor\n"
curl -XDELETE "http://localhost:9130/_/proxy/modules/${SVC_ID}"

# ./gradlew clean generateDescriptors

printf "\n\nPost module descriptor\n"
curl -XPOST 'http://localhost:9130/_/proxy/modules' -d @"${DESCRIPTORDIR}/ModuleDescriptor.json"

printf "\n\nPost deployment descriptor\n"
curl -XPOST 'http://localhost:9130/_/discovery/modules' -d "$DEP_DESC"

printf "\n\nEnable\n"
curl -XPOST 'http://localhost:9130/_/proxy/tenants/diku/install?tenantParameters=loadSample%3Dtest,loadReference%3Dother' -d `echo $DEP_DESC | jq -c '[{id: .srvcId, action: "enable"}]'`

popd
