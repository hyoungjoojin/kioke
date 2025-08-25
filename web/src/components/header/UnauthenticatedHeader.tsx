import { Button } from '@/components/ui/button';
import { Routes } from '@/constant/routes';
import Link from 'next/link';

export default function UnauthenticatedHeader() {
  return (
    <div className='flex justify-between items-center'>
      <span>kioke.</span>

      <div>
        <Link href={Routes.SIGN_IN}>
          <Button>Sign In</Button>
        </Link>
      </div>
    </div>
  );
}
