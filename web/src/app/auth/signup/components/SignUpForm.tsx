'use client';

import { signUp } from '@/app/api/auth';
import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { ErrorCode } from '@/constant/error';
import { Routes } from '@/constant/routes';
import logger from '@/lib/logger';
import { zodResolver } from '@hookform/resolvers/zod';
import { useTranslations } from 'next-intl';
import { redirect } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';
import z from 'zod';

const SignUpFormSchema = z
  .object({
    email: z.email({
      message: 'signup.form.error.invalid-email',
    }),
    password: z.string().nonempty({
      message: 'signup.form.error.empty-field',
    }),
    verifyPassword: z.string().nonempty({
      message: 'signup.form.error.empty-field',
    }),
  })
  .check((ctx) => {
    if (ctx.value.password !== ctx.value.verifyPassword) {
      ctx.issues.push({
        code: 'custom',
        message: 'signup.form.error.unmatching-password',
        input: ctx.value,
        path: ['verifyPassword'],
      });
    }
  });
type SignUpFormSchemaType = z.infer<typeof SignUpFormSchema>;

export default function SignUpForm() {
  const t = useTranslations();

  const signUpForm = useForm<SignUpFormSchemaType>({
    resolver: zodResolver(SignUpFormSchema),
    defaultValues: {
      email: '',
      password: '',
    },
  });

  const signUpFormSubmitHandler = async (credentials: SignUpFormSchemaType) => {
    await signUp(credentials).then((result) =>
      result
        .map((_) => {
          redirect(Routes.SIGN_IN);
        })
        .mapErr((error) => {
          logger.debug(error);

          switch (error.code) {
            case ErrorCode.NETWORK_REQUEST_FAILED:
              toast.error(t('error.network-failed'));
              break;
          }
        }),
    );

    const result = await signUp(credentials);
    if (result.isOk()) {
      redirect(Routes.HOME);
    }
  };

  return (
    <Form {...signUpForm}>
      <form onSubmit={signUpForm.handleSubmit(signUpFormSubmitHandler)}>
        <div className='mt-3 mb-5'>
          <FormField
            name='email'
            control={signUpForm.control}
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>
                    {t('signup.form.fields.email')}
                    <FormMessage t={t} />
                  </FormLabel>
                  <FormControl>
                    <Input placeholder='' {...field} />
                  </FormControl>
                </FormItem>
              );
            }}
          />
        </div>

        <div className='mb-5 flex flex-col gap-5'>
          <FormField
            name='password'
            control={signUpForm.control}
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>
                    {t('signup.form.fields.password')}
                    <FormMessage t={t} />
                  </FormLabel>
                  <FormControl>
                    <Input type='password' placeholder='' {...field} />
                  </FormControl>
                </FormItem>
              );
            }}
          />

          <FormField
            name='verifyPassword'
            control={signUpForm.control}
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>
                    {t('signup.form.fields.verify-password')}
                    <FormMessage t={t} />
                  </FormLabel>
                  <FormControl>
                    <Input type='password' placeholder='' {...field} />
                  </FormControl>
                </FormItem>
              );
            }}
          />
        </div>

        <div>
          <Button type='submit' className='w-full' variant='default'>
            {t('signup.form.submit')}
          </Button>
        </div>
      </form>
    </Form>
  );
}
