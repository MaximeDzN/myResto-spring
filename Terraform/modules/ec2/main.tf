data "aws_ami" "ami_search" {
  most_recent = true
  owners      = ["${var.id_compte}"]

  filter {
    name   = "name"
    values = ["${var.nom_ami}"]
  }
}

resource "aws_instance" "myresto-ec2" {
  ami               = data.aws_ami.ami_search.id
  instance_type     = var.type_instance
  key_name          = var.cle_ssh
  availability_zone = var.zone_dispo
  security_groups   = ["${var.securite_groupe}"]
  tags = {
    Name = "${var.ec2_name}-ec2"
  }

  provisioner "local-exec" {
    when    = destroy
    command = "echo 'EC2 en cours de destruction'"
  }


  provisioner "local-exec" {
    when    = create
    command = "echo 'EC2 en cours de création'"
  }
}

