import { Routes } from '@/constant/routes';
import { getTranslations } from 'next-intl/server';
import Link from 'next/link';

export default async function JournalCollectionsHeader() {
  const t = await getTranslations();

  return (
    <div className='flex gap-2'>
      <span className='italic mr-4'>kioke.</span>
      <span className='hover:underline'>
        <Link href={Routes.HOME}>{t('header.links.home')}</Link>
      </span>
    </div>
  );
}
