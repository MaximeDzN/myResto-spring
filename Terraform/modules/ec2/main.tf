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
    when    = destroy
    command = "echo 'Destroy-time provisioner'"
  }


  provisioner "local-exec" {
    when    = create
    command = "echo 'Create-time provisioner'"
  }
  # provisioner "local-exec" {
  #   when = destroy
  #   inline = [
  #     "ls"
  #   ]
  #   # connection {
  #   #   type        = "ssh"
  #   #   user        = "ubuntu"
  #   #   private_key = file("../../../.aws/${var.cle_ssh}.pem")
  #   #   host        = "${self.public_ip}"
  #   # }
  # }


  # provisioner "remote-exec" {
  #   when   = create
  #   inline = [
  #     "sudo apt update -y",
  #   ] 
  #   # connection {
  #   #   type        = "ssh"
  #   #   user        = "ubuntu"
  #   #   private_key = file("../../../.aws/${var.cle_ssh}.pem")
  #   #   host        = "${self.public_ip}"
  #   # }
  # }
}

