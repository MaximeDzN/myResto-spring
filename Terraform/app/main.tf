#Instanciation module sg
module "sg" {
  source        = "../modules/sg"
  auteur = "${var.auteur}"
}

# Instanciation module eip
module "eip" {
  source        = "../modules/eip"
  auteur = "${var.auteur}"
}
# Instanciation module ec2
module "ec2" {
  source        = "../modules/ec2"
  auteur        = "${var.auteur}"
  type_instance = "t2.micro"
  securite_groupe= "${module.sg.out_sg_nom}"
  ip_public = "${module.eip.out_eip_public_ip}"
  utilisateur_ssh = "ubuntu"
}


resource "aws_eip_association" "eip_assoc" {
  instance_id = module.ec2.out_ec2_id
  allocation_id = module.eip.out_eip_id
}


resource "aws_volume_attachment" "ebs_to_ec2" {
  device_name = "/dev/sdf"
  volume_id   = var.volume_id
  instance_id = module.ec2.out_ec2_id
  skip_destroy = true
  provisioner "remote-exec" {
    inline = [
      "sudo apt update -y",
      "sudo apt install software-properties-common",
      "sudo add-apt-repository --yes --update ppa:ansible/ansible",
      "sudo apt install --yes ansible",
      "git clone -b DevOps https://github.com/${var.git_proprietaire}/${var.git_projet}.git",
      "cd ${var.git_projet}/ansible/",
      "ansible-playbook -i hosts.yml myresto.yml"

    ] 
    connection {
      type        = "ssh"
      user        = "${var.utilisateur_ssh}"
      private_key = file("../../.aws/${var.cle_ssh}.pem")
      host        = "${module.eip.out_eip_public_ip}"
    }
  }
}




