data "aws_ami" "ubuntu_bionic_ami" {
  most_recent = true
  owners      = ["${var.id_compte_ubuntu}"]

  filter {
    name   = "name"
    values = ["${var.nom_ubuntu_ami}"]
  }
}

resource "aws_instance" "myresto-ec2" {
  ami               = data.aws_ami.ubuntu_bionic_ami.id
  instance_type     = var.type_instance
  key_name          = var.cle_ssh
  availability_zone = var.zone_dispo
  security_groups   = ["${var.securite_groupe}"]
  tags = {
    Name = "${var.ec2_name}-ec2"
  }

  provisioner "local-exec" {
    command = "rm -f hosts.yml && echo [masters] \n${var.ip_public}\n >> hosts.yml"
  }

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
      host        = "${self.public_ip}"
    }
  }


}


resource "aws_instance" "myresto-ec2-worker1" {
  ami               = data.aws_ami.ubuntu_bionic_ami.id
  instance_type     = var.type_instance
  key_name          = var.cle_ssh
  availability_zone = var.zone_dispo
  security_groups   = ["${var.securite_groupe}"]
  tags = {
    Name = "${var.ec2_name}-ec2-worker1"
  }

  provisioner "local-exec" {
    command = "echo [workers] \n${var.ip_public}\n >> hosts.yml"
  }

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
      host        = "${self.public_ip}"
    }
  }


}


resource "aws_instance" "myresto-ec2-worker2" {
  ami               = data.aws_ami.ubuntu_bionic_ami.id
  instance_type     = var.type_instance
  key_name          = var.cle_ssh
  availability_zone = var.zone_dispo
  security_groups   = ["${var.securite_groupe}"]
  tags = {
    Name = "${var.ec2_name}-ec2-worker2"
  }

  provisioner "local-exec" {
    command = "echo ${var.ip_public}\n >> hosts.yml"
  }

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
      host        = "${self.public_ip}"
    }
  }


}

