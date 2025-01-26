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
import { redirect } from "next/navigation";
import { useState } from "react";
import { registerWithCredentials } from "@/utils/server/auth";

export const RegisterFormSchema = z
  .object({
    email: z
      .string()
      .nonempty({
        message: "register.email.empty",
      })
      .email({
        message: "register.email.invalid",
      }),
    firstName: z.string().nonempty({
      message: "register.firstName.empty",
    }),
    lastName: z.string().nonempty({
      message: "register.firstName.empty",
    }),
    password: z
      .string()
      .nonempty({
        message: "register.password.empty",
      })
      .min(8, {
        message: "register.password.short",
      }),
    verifyPassword: z.string().nonempty({
      message: "register.verifyPassword.empty",
    }),
  })
  .superRefine((val, ctx) => {
    if (val.password !== val.verifyPassword) {
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        message: "register.verifyPassword.invalid",
        path: ["verifyPassword"],
      });
    }
  });

export default function RegisterForm() {
  const t = useTranslations("");

  const [isError, setIsError] = useState(false);

  const registerForm = useForm<z.infer<typeof RegisterFormSchema>>({
    resolver: zodResolver(RegisterFormSchema),
    defaultValues: {
      email: "",
      firstName: "",
      lastName: "",
      password: "",
      verifyPassword: "",
    },
  });

  const formSubmitHandler = async (
    values: z.infer<typeof RegisterFormSchema>,
  ) => {
    setIsError(false);

    const fields = RegisterFormSchema.parse(values);
    const { email, password, firstName, lastName } = fields;

    const success = await registerWithCredentials(
      email,
      password,
      firstName,
      lastName,
    );

    if (success) {
      redirect("/auth/login");
    } else {
      setIsError(true);
    }
  };

  return (
    <Card className="w-[32rem] dark:bg-zinc-900">
      <CardHeader>
        <CardTitle>{t("register.title")}</CardTitle>
        <CardDescription>{t("register.description")}</CardDescription>
      </CardHeader>

      <CardContent>
        {isError && (
          <p className="text-[0.8rem] font-medium text-destructive">
            {t("error.internal-server-error")}
          </p>
        )}

        <Form {...registerForm}>
          <form
            onSubmit={registerForm.handleSubmit(formSubmitHandler)}
            method="post"
          >
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
                      <FormMessage t={t} />
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
              <div className="w-[48%]">
                <FormField
                  name="firstName"
                  control={registerForm.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="flex justify-between items-center">
                        <div className="text-black dark:text-white">
                          {t("register.firstName.label")}
                        </div>
                        <FormMessage t={t} />
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
              </div>
              <div className="w-[48%]">
                <FormField
                  name="lastName"
                  control={registerForm.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="flex justify-between items-center">
                        <div className="text-black dark:text-white">
                          {t("register.lastName.label")}
                        </div>
                        <FormMessage t={t} />
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
                      <FormMessage t={t} />
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

            <div className="mt-3 mb-5">
              <FormField
                name="verifyPassword"
                control={registerForm.control}
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="flex justify-between items-center">
                      <div className="text-black dark:text-white">
                        {t("register.verifyPassword.label")}
                      </div>
                      <FormMessage t={t} />
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
                {t("register.submit.label")}
              </Button>
            </div>
          </form>
        </Form>
      </CardContent>
    </Card>
  );
}
