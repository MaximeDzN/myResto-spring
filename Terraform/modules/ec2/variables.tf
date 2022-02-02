variable "auteur" {
  type    = string
  default = "projet2"
}

variable "ec2_name" {
  type    = string
  default = "MyResto"
}

variable "zone_dispo" {
  type    = string
  default = "us-east-1b"
}

variable "type_instance" {
  type    = string
  default = "t2.nano"
}

variable "cle_ssh" {
  type    = string
  default = "MyResto"
}

variable "securite_groupe" {
  type    = string
  default = "NULL"
}

variable "ip_public" {
  type    = string
  default = "NULL"
}

variable "utilisateur_ssh" {
  type    = string
  default = "NULL"
}

variable "id_compte_ubuntu" {
  type    = string
  default = "099720109477"
}

variable "nom_ubuntu_ami" {
  type    = string
  default = "ubuntu/images/hvm-ssd/ubuntu-focal-20.04-amd64-server-*"
}

variable "git_proprietaire" {
  type    = string
  default = "MaximeDzN"
}

variable "git_projet" {
  type    = string
  default = "myResto-spring"
}