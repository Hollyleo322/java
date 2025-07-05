minikube start
minikube image load demo:0.0.1-SNAPSHOT
kubectl create deployment demo --image=demo:0.0.1-SNAPSHOT --dry-run=client -o=yaml > deployment.yaml
echo --- >> deployment.yaml
kubectl create service clusterip demo --tcp=8080:8080 --dry-run=client -o=yaml >> deployment.yaml
kubectl apply -f deployment.yaml
sleep 60
kubectl port-forward svc/demo 8080:8080
