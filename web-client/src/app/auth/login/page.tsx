"use client";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { useTranslations } from "next-intl";
import { SelectLanguage } from "@/components/utils/i18n";
import { MainLogo } from "@/components/utils/logo";

const LoginFormSchema = z.object({
  email: z.string().email({ message: "Invalid email address." }),
  password: z.string().nonempty({ message: "Password is empty." }),
});

export default function Login() {
  const t = useTranslations("Login");

  const loginForm = useForm<z.infer<typeof LoginFormSchema>>({
    resolver: zodResolver(LoginFormSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const formSubmitHandler = (values: z.infer<typeof LoginFormSchema>) => {
    console.log(values);
  };

  return (
    <div className="flex w-full h-dvh items-center justify-center bg-gray-50">
      <div className="absolute top-10 left-10">
        <MainLogo />
      </div>

      <Card className="w-1/2">
        <CardHeader>
          <CardTitle>{t("title")}</CardTitle>
          <CardDescription>{t("description")}</CardDescription>
        </CardHeader>

        <CardContent>
          <Form {...loginForm}>
            <form onSubmit={loginForm.handleSubmit(formSubmitHandler)}>
              <div className="my-3">
                <FormField
                  name="email"
                  control={loginForm.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="flex justify-between items-center">
                        <div className="text-black">{t("email.label")}</div>
                        <FormMessage />
                      </FormLabel>
                      <FormControl>
                        <Input
                          placeholder={t("email.placeholder")}
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
                  control={loginForm.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="flex justify-between items-center">
                        <div className="text-black">{t("password.label")}</div>
                        <FormMessage />
                      </FormLabel>
                      <FormControl>
                        <Input
                          type="password"
                          placeholder={t("password.placeholder")}
                          {...field}
                        />
                      </FormControl>
                    </FormItem>
                  )}
                />
                <div className="flex justify-end my-2">
                  <span className=" text-sm hover:underline hover:cursor-pointer">
                    {t("forgot-password")}
                  </span>
                </div>
              </div>

              <div className="my-2">
                <Button type="submit">{t("login-button")}</Button>
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>

      <div className="absolute bottom-10 right-10">
        <SelectLanguage />
      </div>
    </div>
  );
}
