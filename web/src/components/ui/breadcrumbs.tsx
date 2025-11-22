'use client';

import { Routes } from '@/constant/routes';
import useGetJournalByIdQuery from '@/hooks/query/useGetJournalByIdQuery';
import { cn } from '@/lib/utils';
import { MoreHorizontal } from 'lucide-react';
import Link from 'next/link';
import * as React from 'react';

interface JournalBreadcrumbsProps {
  journalId: string;
}

function JournalBreadcrumbs({ journalId }: JournalBreadcrumbsProps) {
  const { data: journal } = useGetJournalByIdQuery({
    path: { journalId },
  });

  return (
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbPage>{journal && journal.title}</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  );
}

interface PageBreadcrumbsProps {
  journalId: string;
  pageId: string;
}

function PageBreadcrumbs({ journalId, pageId }: PageBreadcrumbsProps) {
  const { data: journal } = useGetJournalByIdQuery({
    path: { journalId },
  });

  return (
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink href={Routes.JOURNAL(journalId)}>
            {journal && journal.title}
          </BreadcrumbLink>
        </BreadcrumbItem>

        <BreadcrumbSeparator />

        <BreadcrumbItem>
          <BreadcrumbPage>
            {journal && journal.pages.find((page) => page.id === pageId)?.title}
          </BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  );
}

function Breadcrumb({ ...props }: React.ComponentProps<'nav'>) {
  return <nav aria-label='breadcrumb' data-slot='breadcrumb' {...props} />;
}

function BreadcrumbList({ className, ...props }: React.ComponentProps<'ol'>) {
  return (
    <ol
      data-slot='breadcrumb-list'
      className={cn(
        'text-muted-foreground flex flex-wrap items-center gap-1.5 text-sm break-words sm:gap-2.5',
        className,
      )}
      {...props}
    />
  );
}

function BreadcrumbItem({ className, ...props }: React.ComponentProps<'li'>) {
  return (
    <li
      data-slot='breadcrumb-item'
      className={cn('inline-flex items-center gap-1.5', className)}
      {...props}
    />
  );
}

function BreadcrumbLink({
  className,
  ...props
}: React.ComponentProps<typeof Link> & {
  asChild?: boolean;
}) {
  return <Link className={cn('hover:underline', className)} {...props} />;
}

function BreadcrumbPage({ className, ...props }: React.ComponentProps<'span'>) {
  return (
    <span
      data-slot='breadcrumb-page'
      role='link'
      aria-disabled='true'
      aria-current='page'
      className={cn('text-foreground font-normal', className)}
      {...props}
    />
  );
}

function BreadcrumbSeparator({
  children,
  className,
  ...props
}: React.ComponentProps<'li'>) {
  return (
    <li
      data-slot='breadcrumb-separator'
      role='presentation'
      aria-hidden='true'
      className={cn('[&>svg]:size-3.5', className)}
      {...props}
    >
      {children ?? '/'}
    </li>
  );
}

function BreadcrumbEllipsis({
  className,
  ...props
}: React.ComponentProps<'span'>) {
  return (
    <span
      data-slot='breadcrumb-ellipsis'
      role='presentation'
      aria-hidden='true'
      className={cn('flex size-9 items-center justify-center', className)}
      {...props}
    >
      <MoreHorizontal className='size-4' />
      <span className='sr-only'>More</span>
    </span>
  );
}

export {
  JournalBreadcrumbs,
  PageBreadcrumbs,
  Breadcrumb,
  BreadcrumbList,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbPage,
  BreadcrumbSeparator,
  BreadcrumbEllipsis,
};
