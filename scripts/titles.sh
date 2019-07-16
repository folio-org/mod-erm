#!/bin/bash
curl --header "X-Okapi-Tenant: diku" http://localhost:8080/erm/export/index?stats=true -X GET
