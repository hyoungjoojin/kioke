import Image from 'next/image';

export const MainLogo = () => {
  return (
    <div className='flex justify-center items-center'>
      <Image
        src='/assets/logo/kioke-main-logo.svg'
        alt='Kioke Logo'
        width={32}
        height={32}
      />
    </div>
  );
};
