variable "zone_dispo" {
  type    = string
  default = "us-east-1"
}

variable "auteur" {
  type    = string
  default = "groupe2"
}

variable "volume_id"{
  type = string
  default = "vol-0c840123584a1acce"
}

variable "utilisateur_ssh" {
  type    = string
  default = "ubuntu"
}

variable "cle_ssh" {
  type    = string
  default = "MyResto"
}


variable "git_projet" {
  type    = string
  default = "myResto-spring"
}