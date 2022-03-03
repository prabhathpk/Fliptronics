package com.ppk.global;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;

import com.ppk.model.Product;

public class GlobalData
{
public static List<Product> cart;
static
{
	cart =new ArrayList<Product>();
}
}
