"use client";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { z } from "zod";
import { useTranslations } from "next-intl";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";

export const RegisterFormSchema = z
  .object({
    email: z.string().email(),
    firstName: z.string().nonempty(),
    lastName: z.string().nonempty(),
    password: z.string().min(8),
    verifyPassword: z.string().nonempty(),
  })
  .refine((values) => values.password === values.verifyPassword, {
    message: "Passwords don't match",
  });

export default function RegisterForm() {
  const t = useTranslations("");

  const registerForm = useForm<z.infer<typeof RegisterFormSchema>>({
    resolver: zodResolver(RegisterFormSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const formSubmitHandler = async (
    values: z.infer<typeof RegisterFormSchema>,
  ) => {};

  return (
    <Card className="w-[28rem] dark:bg-zinc-900">
      <CardHeader>
        <CardTitle>{t("register.title")}</CardTitle>
        <CardDescription>{t("register.description")}</CardDescription>
      </CardHeader>

      <CardContent>
        <Form {...registerForm}>
          <form onSubmit={registerForm.handleSubmit(formSubmitHandler)}>
            <div className="my-3">
              <FormField
                name="email"
                control={registerForm.control}
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="flex justify-between items-center">
                      <div className="text-black dark:text-white">
                        {t("register.email.label")}
                      </div>
                      <FormMessage>
                        {t("register.email.invalid-email")}
                      </FormMessage>
                    </FormLabel>
                    <FormControl>
                      <Input
                        placeholder={t("register.email.placeholder")}
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
            </div>

            <div className="my-3 w-full flex justify-between">
              <FormField
                name="firstName"
                control={registerForm.control}
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="flex justify-between items-center">
                      <div className="text-black dark:text-white">
                        {t("register.firstName.label")}
                      </div>
                      <FormMessage>
                        {t("register.firstName.invalid-name")}
                      </FormMessage>
                    </FormLabel>
                    <FormControl>
                      <Input
                        placeholder={t("register.firstName.placeholder")}
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                )}
              />

              <FormField
                name="lastName"
                control={registerForm.control}
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="flex justify-between items-center">
                      <div className="text-black dark:text-white">
                        {t("register.lastName.label")}
                      </div>
                      <FormMessage>
                        {t("register.lastName.invalid-name")}
                      </FormMessage>
                    </FormLabel>
                    <FormControl>
                      <Input
                        placeholder={t("register.lastName.placeholder")}
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
            </div>

            <div className="my-3">
              <FormField
                name="password"
                control={registerForm.control}
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="flex justify-between items-center">
                      <div className="text-black dark:text-white">
                        {t("register.password.label")}
                      </div>
                      <FormMessage>{t("register.password.empty")}</FormMessage>
                    </FormLabel>
                    <FormControl>
                      <Input
                        type="password"
                        placeholder={t("register.password.placeholder")}
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
            </div>

            <div className="my-3">
              <FormField
                name="verifyPassword"
                control={registerForm.control}
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="flex justify-between items-center">
                      <div className="text-black dark:text-white">
                        {t("register.verifyPassword.label")}
                      </div>
                      <FormMessage>
                        {t("register.verifyPassword.invalid")}
                      </FormMessage>
                    </FormLabel>
                    <FormControl>
                      <Input
                        type="password"
                        placeholder={t("register.verifyPassword.placeholder")}
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
            </div>

            <div className="flex justify-center">
              <Button type="submit" className="w-full">
                Sign up
              </Button>
            </div>
          </form>
        </Form>
      </CardContent>
    </Card>
  );
}
