import BaseHeader from '@/components/header/BaseHeader';
import BaseLayout from '@/components/layout/BaseLayout';
import type { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'kioke - Notifications',
};

interface LayoutProps {
  children: React.ReactNode;
}

export default function Layout({ children }: Readonly<LayoutProps>) {
  return (
    <BaseLayout header={<BaseHeader selectedTab='none' />} main={children} />
  );
}
